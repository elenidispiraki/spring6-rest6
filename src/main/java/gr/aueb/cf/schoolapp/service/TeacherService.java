package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Attachment;
import gr.aueb.cf.schoolapp.model.PersonalInfo;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.repository.PersonalInfoRepository;
import gr.aueb.cf.schoolapp.repository.TeacherRepository;
import gr.aueb.cf.schoolapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final  TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final Mapper mapper;


    @Transactional(rollbackOn = Exception.class)
    public TeacherReadOnlyDTO saveTeacher(TeacherInsertDTO teacherInsertDTO, MultipartFile amkaFile)
            throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {

        if (userRepository.findByVat(teacherInsertDTO.getUser().getVat()).isPresent()) {
            throw new AppObjectAlreadyExistsException("User", "User with Vat: " + teacherInsertDTO.getUser().getVat() + " already exists");
        }

        if (userRepository.findByUsername(teacherInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExistsException("User", "User with Username:" + teacherInsertDTO.getUser().getUsername() + "already exists");
        }

        if (personalInfoRepository.findByAmka(teacherInsertDTO.getPersonalInfo().getAmka()).isPresent()) {
            throw new AppObjectAlreadyExistsException("PersonalInfo", "Personal info with AMKA: " + teacherInsertDTO.getPersonalInfo().getAmka() + " already exists");
        }

        if (personalInfoRepository.findByIdentityNumber(teacherInsertDTO.getPersonalInfo().getIdentityNumber()).isPresent()) {
            throw new AppObjectAlreadyExistsException("PersonalInfo", "Personal info with Identity Number: " + teacherInsertDTO.getPersonalInfo().getIdentityNumber() + " already exists");
        }

        Teacher teacher = mapper.mapToTeacherEntity(teacherInsertDTO);

        saveAmkaFile(teacher.getPersonalInfo(), amkaFile);

        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapper.mapToTeacherReadOnlyDTO(savedTeacher);

    }

    @Transactional(rollbackOn = Exception.class)
    public void saveAmkaFile(PersonalInfo personalInfo, MultipartFile amkaFile) throws IOException {
        if (amkaFile != null && !amkaFile.isEmpty()) {
            String originaFilelName = amkaFile.getOriginalFilename();
            String savedName = UUID.randomUUID() + getFileExtension(originaFilelName);
            String uploadDir = "uploads/";
            Path filepath = Paths.get(uploadDir + savedName);
            Files.createDirectories(filepath.getParent());
            Files.write(filepath, amkaFile.getBytes());

            Attachment attachment = new Attachment();
            attachment.setFilename(originaFilelName);
            attachment.setSavedName(savedName);
            attachment.setFilePath(filepath.toString());
            attachment.setContentType(amkaFile.getContentType());
            attachment.setExtension(getFileExtension(originaFilelName));

            personalInfo.setAmkaFile(attachment);

        }

    }

    public String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }

    public Page<TeacherReadOnlyDTO> getPaginatedTeachers(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return teacherRepository.findAll(pageable).map(mapper::mapToTeacherReadOnlyDTO);
    }

    public Page<TeacherReadOnlyDTO> getPaginatedSortedTeachers(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return teacherRepository.findAll(pageable).map(mapper::mapToTeacherReadOnlyDTO);
    }



}




//@Service
//public class TeacherService {
//
//    private  TeacherRepository teacherRepository;
//    private  UserRepository userRepository;
//    private  PersonalInfoRepository personalInfoRepository;
//    private  Mapper mapper;
//
//    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, PersonalInfoRepository personalInfoRepository, Mapper mapper) {
//        this.teacherRepository = teacherRepository;
//        this.userRepository = userRepository;
//        this.personalInfoRepository = personalInfoRepository;
//        this.mapper = mapper;
//    }
//}
