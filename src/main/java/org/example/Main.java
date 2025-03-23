package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookService service = new BookService(new DAODatabase());

        printLibrary(service);

        System.out.print("Title of book: ");
        String title = scanner.nextLine();
        System.out.print("First name of book's author: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name of book's author: ");
        String lastName = scanner.nextLine();
        Book book = new Book(title, firstName, lastName);
        service.persist(book);

        System.out.println("Books database after persist:");
        printLibrary(service);

        System.out.print("Which book do you want to delete (please insert an id): ");
        int id = scanner.nextInt();
        if (service.findById(id).isPresent()) {
            Book bookToDelete = service.findById(id).get();
            service.delete(bookToDelete);
        }

        System.out.println("Books database after delete book #:" + id);
        printLibrary(service);
    }

    private static void printLibrary(BookService service) {
        if (service.findAll().isPresent()) {
            List<Book> books = service.findAll().get();
            System.out.println("Books database:");
            books.forEach(System.out::println);
        } else {
            System.out.println("No data to print!");
        }
    }
}
