package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;

import java.util.List;

public interface ITeacherService {
	Teacher insertTeacher(TeacherDTO teacherToInsert) throws TeacherDAOException;
	
	Teacher updateTeacher(TeacherDTO teacherToUpdate) 
			throws TeacherDAOException, TeacherNotFoundException;
	
	void deleteTeacher(int id) 
			throws TeacherDAOException, TeacherNotFoundException;
	
	List<Teacher> getTeachersByLastName(String lastname) throws TeacherDAOException;
	Teacher getTeacherById(int id) throws TeacherDAOException;

}
