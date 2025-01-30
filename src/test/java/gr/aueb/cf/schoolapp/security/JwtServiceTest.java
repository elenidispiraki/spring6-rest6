package gr.aueb.cf.schoolapp.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @Test
    void testGenerateToken() {
        JwtService jwtService = new JwtService();

        // Δημιουργούμε ένα JWT token για έναν χρήστη με ρόλο USER
        String token = jwtService.generateToken("springuser", "USER");

        // Εκτυπώνουμε το token για να το δούμε
        System.out.println("Generated Token: " + token);

        // Έλεγχος ότι το token δεν είναι null ή κενό
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
