package com.example.graduationProject.enumeration;

public enum SUBJECT {
    NEW_YEAR("NewYear"),
    FEBRUARY_23("February23"),
    MARCH_8("March8"),
    LAST_BELL("Lastbell"),
    SEPTEMBER_1("September1"),
    TEACHERS_DAY("TeachersDay"),
    EDUCATORS_DAY("EducatorsDay"),
    BIRTHDAY("Birthday"),
    COACHS_DAY("CoachsDay"),
    MEDICAL_WORKERS_DAY("MedicalWorkersDay"),
    WEDDING("wedding"),
    CORPORATE("corporate"),
    ANY_DAY("aneday");

    private final String command;

    SUBJECT(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    // Метод для получения соответствующего enum по строке команды
    public static SUBJECT fromCommand(String command) {
        for (SUBJECT subject : values()) {
            if (subject.getCommand().equals(command)) {
                return subject;
            }
        }
        return null; // Или можно выбросить исключение, если команда не найдена
    }
}
