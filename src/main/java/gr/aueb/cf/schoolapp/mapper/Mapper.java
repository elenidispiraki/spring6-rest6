package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.dto.PersonalInfoInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.UserInsertDTO;
import gr.aueb.cf.schoolapp.model.PersonalInfo;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    //private final PasswordEncoder passwordEncoder

    public Teacher mapToTeacherEntity(TeacherInsertDTO teacherInsertDTO){
        Teacher teacher = new Teacher();
        teacher.setIsActive(teacherInsertDTO.getIsActive());

        User user = new User();
        UserInsertDTO userInsertDTO = teacherInsertDTO.getUser();
        user.setFirstname(userInsertDTO.getFirstname());
        user.setLastname(userInsertDTO.getLastname());
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(userInsertDTO.getPassword());  //todo
        user.setFatherName(userInsertDTO.getFatherName());
        user.setMotherName(userInsertDTO.getMotherName());
        user.setFatherLastname(userInsertDTO.getLastname());
        user.setMotherLastname(userInsertDTO.getLastname());
        user.setDateOfBirth(userInsertDTO.getDateOfBirth());
        user.setRole(userInsertDTO.getRole());
        user.setGender(userInsertDTO.getGender());
        user.setIsActive(userInsertDTO.getIsActive());
        user.setVat(userInsertDTO.getVat());
        teacher.setUser(user);

        PersonalInfo personalInfo = new PersonalInfo();
        PersonalInfoInsertDTO personalInfoInsertDTO = teacherInsertDTO.getPersonalInfo();
        personalInfo.setAmka(personalInfoInsertDTO.getAmka());
        personalInfo.setIdentityNumber(personalInfoInsertDTO.getIdentityNumber());
        personalInfo.setPlaceOfBirth(personalInfoInsertDTO.getPlaceOfBirth());
        personalInfo.setMunicipalityOfRegistration(personalInfoInsertDTO.getMunicipalityOfRegistration());
        teacher.setPersonalInfo(personalInfo);

        return teacher;
    }

}
