package com.java8.FileHandling;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception{

        int choice = 99;
        Scanner scan1 = new Scanner(System.in);
        Scanner scan2 = new Scanner(System.in);
        ArrayList<Person> people = new ArrayList<>();
        File file = new File("src/com/java8/FileHandling/record.txt");
        ObjectOutputStream oouts = null;
        ObjectInputStream oinputs = null;

        if_person ps = (ArrayList<Person> p) -> {
            System.out.println("Id: ");
            var id = scan2.nextInt();
            System.out.println("name: ");
            var name = scan1.nextLine();
            System.out.println("email: ");
            String email = scan1.nextLine();
            System.out.println("phone: ");
            var phone = scan1.nextLine();
            System.out.println("age: ");
            var age = scan2.nextInt();
            p.add(new Person(id, name, email, phone, age));
        };
        if(file.isFile()){
            try{
                oinputs = new ObjectInputStream(new FileInputStream(file));
                people = (ArrayList<Person>) oinputs.readObject();
                oinputs.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            System.out.println("File not exist");
        }
        do{
            choice = menu();
            switch (choice) {
                case 1 -> {
                    System.out.println("how many user?: ");
                    int userCount = scan2.nextInt();
                    while (userCount > 0){
                        ps.create(people);
                        userCount -= 1;
                    }
                    // process file hare
                    try{
                        oouts = new ObjectOutputStream(new FileOutputStream(file));
                        oouts.writeObject(people);
                        oouts.close();
                        System.out.println("user created");
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                }
                case 2 -> {
                    if(file.isFile()){
                        try{
                            oinputs = new ObjectInputStream(new FileInputStream(file));
                            people = (ArrayList<Person>) oinputs.readObject();
                            oinputs.close();
                            people.stream().forEach(System.out::println);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                case 3 -> {


                    System.out.println("search id: ");
                    int uid = scan2.nextInt();
                    var psn = people.stream()
                            .filter(person -> person.getId() == uid)
                            .collect(Collectors.toList());
                    System.out.println("--------------------------------------------");
                    if(!psn.isEmpty())
                        psn.forEach(System.out::println);
                    else
                        System.out.println("Record not found");
                    System.out.println("--------------------------------------------");
                }
                case 4 -> {
                    var psn = people.stream()
                            .sorted((person1, person2)-> person1.getId() - person2.getId())
                            .collect(Collectors.toList());
                    System.out.println("--------------------------------------------");
                    if(!psn.isEmpty())
                        psn.forEach(System.out::println);
                    else
                        System.out.println("Record not found");
                    System.out.println("--------------------------------------------");
                }
                case 5 -> {
                    var psn = (ArrayList<Person>) people.stream()
                            .sorted((person1, person2)-> person1.getId() - person2.getId())
                            .collect(Collectors.toList());
                    System.out.println("--------------------------------------------");
                    if(!psn.isEmpty()){
                        try{
                        oouts = new ObjectOutputStream(new FileOutputStream(file));
                        oouts.writeObject(psn);
                        oouts.close();
                        psn.forEach(System.out::println);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else
                        System.out.println("Record not found");
                    System.out.println("--------------------------------------------");
                }
                case 6 -> {
                    System.out.println("Delete id: ");
                    int uid = scan2.nextInt();
                    List<Person> lp = people.stream()
                            .filter(person -> person.getId() != uid)
                            .collect(Collectors.toList());
                 // break point-------------------------------------------------->>>>>>>>>>>>>>>>>
                }
                case 7 -> System.out.println("Delete user");
            }

        }while (choice != 99);
    }
    // -------------------------------------------------------------- functions
    // ============================ MENU FUNCTION;
    public static int menu(){
        var scan = new Scanner(System.in);
        System.out.println("Choose from the menu ..!");
        System.out.println("1. Create user");
        System.out.println("2. Show users");
        System.out.println("3. Search Users");
        System.out.println("4. Sort Users from UI");
        System.out.println("5. Sort Users from file");
        System.out.println("6. Delete Users from UI");
        System.out.println("7. Delete Users from file");
        return scan.nextInt();

    }

}
