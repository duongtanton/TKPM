package com.tkpm.studentsmanagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tkpm.studentsmanagement.constant.Role;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.entity.*;
import com.tkpm.studentsmanagement.repository.RoleRepository;
import com.tkpm.studentsmanagement.util.TimestampUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.repository.UserRepository;
import com.tkpm.studentsmanagement.service.IUserService;

import jakarta.transaction.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> listUserEntity = userRepository.findAll();
        return modelMapper.map(listUserEntity, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    @Override
    public SimpleResponse<UserDTO> findAll(UserSearchDTO userSearchDTO, Pageable pageable) {
        SimpleResponse<UserDTO> simpleResponse = new SimpleResponse<>();
        UserDTO userDTOSearch = new UserDTO();
        BeanUtils.copyProperties(userSearchDTO, userDTOSearch, "createdDate", "updatedDate", "createdBy", "updatedBy");

        userDTOSearch.setCreatedDate(TimestampUtil.convertStringToTimestamp(userSearchDTO.getCreatedDate()));
        userDTOSearch.setUpdatedDate(TimestampUtil.convertStringToTimestamp(userSearchDTO.getUpdatedDate()));

        Page<UserEntity> userEntityPage = userRepository.findAll(
                userDTOSearch.getName(),
                userDTOSearch.getCreatedDate(),
                userDTOSearch.getUpdatedDate(),
                userSearchDTO.getCreatedBy(),
                userSearchDTO.getUpdatedBy(),
                pageable);
        List<UserDTO> listT = modelMapper.map(userEntityPage.get().collect(Collectors.toList()),
                new TypeToken<List<UserDTO>>() {
                }.getType());

        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages(pageable));
        simpleResponse.setListT(listT);
        return simpleResponse;
    }

    @Override
    public List<UserDTO> findAllById(List<Long> ids) {
        List<UserEntity> listUserEntity = modelMapper.map(userRepository.findAllById(ids),
                new TypeToken<List<UserEntity>>() {
                }.getType());
        return modelMapper.map(listUserEntity, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    @Override
    public UserDTO findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        Hibernate.initialize(userEntity.getCreatedBy());
        Hibernate.initialize(userEntity.getUpdatedBy());
        Hibernate.initialize(userEntity.getRoles());
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO findByUsername(String username) {
        UserEntity userEntityRes = userRepository.findByUsername(username);
        return modelMapper.map(userEntityRes, UserDTO.class);
    }

    @Override
    public UserDTO findByUsernameAndEmail(String username, String email) {

        UserEntity userEntity = userRepository.findByUsernameAndEmail(username, email);
        UserDTO userDTO = null;
        try {
            userDTO = modelMapper.map(userEntity, UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        UserEntity userEntityRes = userRepository.save(userEntity);
        return modelMapper.map(userEntityRes, UserDTO.class);
    }

    @Override
    public UserDTO save(UserCreateDTO userCreateDTO, Long currentUserId) throws JsonProcessingException {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userCreateDTO, userDTO, "roles");
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (String roleStr : userCreateDTO.getRoles()) {
            Role role = Role.lookup(roleStr);
            roleEntities.add(roleRepository.findByName(role.name()));
        }
        userDTO.setEnable(false);
        userDTO.setRoles(modelMapper.map(roleEntities, new TypeToken<RoleDTO>() {
        }.getType()));
        userDTO.setCreatedBy(modelMapper.map(userRepository.findById(currentUserId), UserDTO.class));
        userDTO.setUpdatedBy(modelMapper.map(userRepository.findById(currentUserId), UserDTO.class));
        return save(userDTO);
    }

    @Override
    public List<UserDTO> save(List<UserDTO> listUser) {
        List<UserEntity> listUserEntity = modelMapper.map(listUser, new TypeToken<List<UserEntity>>() {
        }.getType());
        return modelMapper.map(userRepository.saveAll(listUserEntity),
                new TypeToken<List<UserDTO>>() {
                }.getType());
    }

    @Override
    public Boolean update(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userDTO.getId()).orElse(null);
        try {
            userEntity.setName(userDTO.getName());
            userEntity.setEnable(userDTO.getEnable());
            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void activateAccount(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail());
        if (userEntity.getEnable()) {
            throw new RuntimeException("This account has already activated");
        }
        userEntity.setEnable(true);
        userRepository.save(userEntity);
    }

    @Override
    public Integer totalPages(Pageable pageable) {
        Long count = userRepository.count();
        return (Integer) (int) Math.ceil((double) count / pageable.getPageSize());
    }

    @Override
    public Boolean disable(DisableRequest disableRequest) {
        for (Long id : disableRequest.getIds()) {
            UserEntity userEntity = userRepository.findById(id).orElse(null);
            if (userEntity == null || !userEntity.getEnable()) {
                throw new RuntimeException("This account hasn't been activated yet");
            }
            userEntity.setEnable(false);
            userRepository.save(userEntity);
        }
        return true;
    }

    @Override
    public void exportExcel(Long[] ids, HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=User_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("User");
        int rowNum = 0;
        Row _row = sheet.createRow(rowNum++);
        Cell _cell1 = _row.createCell(0);
        _cell1.setCellValue("id");
        Cell _cell2 = _row.createCell(1);
        _cell2.setCellValue("name");
        Cell _cell3 = _row.createCell(2);
        _cell3.setCellValue("email");
        Cell _cell4 = _row.createCell(3);
        _cell4.setCellValue("roles");
        Cell _cell5 = _row.createCell(4);
        _cell5.setCellValue("isEnable");
        Cell _cell6 = _row.createCell(5);
        _cell6.setCellValue("createdDate");
        Cell _cell7 = _row.createCell(6);
        _cell7.setCellValue("updatedDate");
        Cell _cell8 = _row.createCell(7);
        _cell8.setCellValue("createdBy");
        Cell _cell9 = _row.createCell(8);
        _cell9.setCellValue("updatedBy");
        List<UserEntity> listUser;
        if (ids != null) {
            listUser = userRepository.findAllById(Arrays.asList(ids));
        } else {
            listUser = userRepository.findAll();
        }
        for (UserEntity user : listUser) {
            Row row = sheet.createRow(rowNum++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(user.getId());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(user.getName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(user.getEmail());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.joining(",")));
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(user.getEnable());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(dateFormatter.format(user.getCreatedDate()));
            Cell cell7 = row.createCell(6);
            cell7.setCellValue(dateFormatter.format(user.getUpdatedDate()));
            Cell cell8 = row.createCell(7);
            cell8.setCellValue(user.getCreatedBy().getEmail());
            Cell cell9 = row.createCell(8);
            cell9.setCellValue(user.getUpdatedBy().getEmail());
        }

        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}