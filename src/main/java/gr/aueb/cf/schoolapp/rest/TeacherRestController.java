package gr.aueb.cf.schoolapp.rest;



import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.exceptions.TeacherNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/teachers")
public class TeacherRestController {

    @Inject
    private ITeacherService teacherService;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacherByLastname(@QueryParam("lastname") String lastname) {
        try {
            List<Teacher> teachers = teacherService.getTeachersByLastName(lastname);
            if (teachers.size() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(200).entity(teachers).build();
        } catch (TeacherDAOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }

    @Path("/{teacherId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacherById(@PathParam("teacherId") int teacherId) {
        try {
            Teacher teacher = teacherService.getTeacherById(teacherId);
            if (teacher == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.status(200).entity(teacher).build();
        } catch (TeacherDAOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(TeacherDTO teacherDTO, @Context UriInfo uriInfo) {
        try {
            Teacher teacher = teacherService.insertTeacher(teacherDTO);
            if (teacher == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            TeacherDTO insertedTeacherDTO = new TeacherDTO(
                    teacher.getId(),
                    teacher.getFirstname(),
                    teacher.getLastname()
            );

            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.created(uriBuilder.path(String.valueOf(insertedTeacherDTO.getId())).build())
                    .entity(insertedTeacherDTO).build();
        } catch (TeacherDAOException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }

    @Path("/{teacherId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("teacherId") int teacherId) {
        try {
            Teacher teacher = teacherService.getTeacherById(teacherId);
            teacherService.deleteTeacher(teacherId);

            TeacherDTO deletedTeacher = new TeacherDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname());
            return Response.status(200).entity(deletedTeacher).build();
        } catch (TeacherDAOException e1) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        } catch (TeacherNotFoundException e2) {
            return Response
                    .status(404)
                    .entity("Not Found")
                    .build();
        }
    }

    @Path("/{teacherId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@PathParam("teacherId") int teacherId, TeacherDTO teacherDTO) {
        try {
            teacherDTO.setId(teacherId);
            Teacher teacher = teacherService.updateTeacher(teacherDTO);

            if (teacher == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            TeacherDTO updatedTeacher = new TeacherDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname());
            return Response.status(200).entity(updatedTeacher).build();
        } catch (TeacherNotFoundException e2) {
            return Response
                    .status(404)
                    .entity("Not Found")
                    .build();
        } catch (TeacherDAOException e1) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Server Error")
                    .build();
        }
    }
}
