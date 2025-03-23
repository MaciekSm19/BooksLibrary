package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookService service = new BookService(new DAODataBase());

        List<Book> books = service.findAll();
        System.out.println("Books database:");
        books.forEach(System.out::println);

        System.out.print("Title of book: ");
        String title = scanner.nextLine();
        System.out.print("First name of book's author: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name of book's author: ");
        String lastName = scanner.nextLine();
        Book book = new Book(title, firstName, lastName);
        service.persist(book);

        books = service.findAll();
        System.out.println("Books database after persist:");
        books.forEach(System.out::println);

        System.out.print("Which book do you want to delete (please insert an id): ");
        int id = scanner.nextInt();
        Book bookToDelete = service.findById(id);
        service.delete(bookToDelete);

        books = service.findAll();
        System.out.println("Books database after delete book #:" + id);
        books.forEach(System.out::println);
    }
}
