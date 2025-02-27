package com.example.read_write_db.commands;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Sherif.Abdulraheem 27/02/2025 - 23:50
 **/
@Component
public class ShowBooks implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 0){
            System.out.println("No cli arguments provided");
            return;
        }

        for (String arg : args) {
            System.out.println("Args: " + arg);
        }
    }
}
