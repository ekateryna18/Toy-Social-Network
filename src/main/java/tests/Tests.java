package tests;

import domain.Utilizator;
import domain.validators.UtilizatorValidator;
import domain.validators.ValidationException;
import java.util.*;

public class Tests {

    public static void run() throws Exception{
        testValidator();
        System.out.println("User validator tests succesful!\n");
    }
    private static void testValidator(){
        UtilizatorValidator val = new UtilizatorValidator();
        try {
            val.validate(new Utilizator("",""));
            assert false;
        }
        catch (ValidationException e){
            assert e.getMessage().equals("Id cannot be null!\nFirst name must have at least 3 letters\nFirst name must start with capital letter and contain just letters\nLast name must have at least 3 letters\nLast name must start with capital letter and contain just letters\n");
        }
        Utilizator u = new Utilizator("Mihai", "Popa");
        u.setId(0L);
        val.validate(u);

        try{
            u.setFirstName("a484ferv");
            val.validate(u);
            assert false;
        }
        catch (ValidationException e){
            assert e.getMessage().equals("First name must start with capital letter and contain just letters\n");
        }
    }


}
