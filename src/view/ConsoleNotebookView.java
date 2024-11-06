package view;

import model.Note;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleNotebookView implements NotebookView {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void showNotes(List<Note> notes) {
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            for (Note note : notes) {
                System.out.println(note);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    // Метод для ввода только даты (без времени)
    @Override
    public LocalDateTime getDateInput() {
        System.out.println("Введите начальную дату недели (yyyy-MM-dd):");
        String input = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(input + "T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // Метод для ввода даты и времени в формате yyyy-MM-dd HH:mm
    @Override
    public LocalDateTime getDateTimeInput() {
        String input;
        LocalDateTime dateTime = null;

        while (dateTime == null) {
            System.out.println("Введите дату и время (yyyy-MM-dd HH:mm), или только дату (yyyy-MM-dd):");
            input = scanner.nextLine();

            DateTimeFormatter formatterWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter formatterWithoutTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Попытка распарсить сначала с временем
            try {
                dateTime = LocalDateTime.parse(input, formatterWithTime);
            } catch (Exception e) {
                // Если не получилось, пробуем распарсить только дату и устанавливаем время на 00:00
                try {
                    LocalDate date = LocalDate.parse(input, formatterWithoutTime);
                    dateTime = date.atStartOfDay(); // Устанавливаем время на полночь
                } catch (Exception ex) {
                    // Если не получилось распарсить вообще, выводим ошибку
                    System.out.println("Неверный формат ввода. Попробуйте снова.");
                }
            }
        }

        return dateTime;
    }


    @Override
    public String getDescriptionInput() {
        System.out.println("Enter note description:");
        return scanner.nextLine();
    }

    @Override
    public String getFileNameInput() {
        System.out.println("Enter file name:");
        return scanner.nextLine();
    }
}
