package Package1.hospitalservice;

import Package1.room.Room;

public interface AppointmentMediator {
    String bookAppointment(String patientId,
                           String doctorName,
                           String appointmentDate,
                           String roomId,
                           int daysOfStay,
                           String appointmentType) throws Exception;
}

