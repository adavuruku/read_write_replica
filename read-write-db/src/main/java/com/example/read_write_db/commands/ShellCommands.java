package com.example.read_write_db.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.security.Key;

/**
 * Created by Sherif.Abdulraheem 28/02/2025 - 00:10
 **/
@ShellComponent
public class ShellCommands {
    /**
     * @ShellMethod(
     *
     * key -> the name in shell default is the method name that it decorates -> u can specify multiple
     * names (keys) for one method e.g key = {"task1", "task2"} or just one name key="task1"
     *
     *  value -> description of the command, the shell display this value when the help command is executed.
     *  help command list all the possible available shell commands and the value by the side as description
     *  e.g value= "Show other methods" >> help will display [task1  show all books]
     *
     *  prefix -> defines how options are formatted. thats how argument values are passed to the command
     *  default is -- [list of argument values], this option can be use to change this e.g prefix ="/"
     *  to pass value to the command . task1 / 2 sherif
     *  instead of task1 -- 2 sherif
     *
     *  group -> is use to group command such than when help command is execusted the command are displayed as group
     *  example.
     *  group = "ServiceA"
     *  help
     *  ServiceA
     *      task1 task1 details
     *      task2 task2 desc
     *
     *  interactionMode -> use to define if command can be executed in interacctive or non interactive
     *  shell platform. interactive (allow communication as method process) allow interaction and can
     *  only be executed from spring shell
     *  example
     *  task1 --age 2
     *  non-interactive dont allow inerraction (like executing from automated script or ci/cd
     *  example echo "run-task --age 2" | mvn spring-boot: run
     *  or echo "run-task --age 2" | ./shell_app
     *  as method is executed, all (execute in both environment type). default is All
     *
     *
     *  )
     */
    @ShellMethod(key = "show_books", value = "Show all books")
    public void showBooks() {
        System.out.println("Show Books");
    }

    /**
     *
     * @ShellOption -> to define method params
     *
     * defaultValue -> the default when value not provided for the params
     *
     * value -> a placeholder for the param name can be multiple names e.g value ={"-a", "--age"}
     * or just value="age"
     *
     * arity -> specify the number of value passed in terms of array, default is 1
     * arrity=2  int[] fruits. it means fruits array must have 3 items
     *
     * help -> description for the params,  default ""
     *
     * interactive -> specify if params are only pass when function is executing or before executing , default false(befaore executing)
     */

    /***
     *
     * @param name
     * @return
     */
    @ShellMethod(key = "show_other_methods", value = "Show other methods")
    public String showOtherMethods(@ShellOption(defaultValue = "def") String name) {
        return "Show other methods";
    }
}
