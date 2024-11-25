package com.Elaa.demo.Service;

import com.Elaa.demo.Entity.User;
import com.Elaa.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final String BASE_URL = "http://localhost:8080/api/users"; // Replace with your application's URL

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getPendingUsers() {
        return userRepository.findByValidatedFalse();
    }

    public User addUser(User user) {
        user.setValidated(false);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Sends a validation email to the user with a unique link.
     *
     * @param userId The ID of the user to validate.
     */
    public void sendValidationEmail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String validationLink = BASE_URL + "/validate/" + userId;
        String emailBody = "Bonjour " + user.getFirstName() + ",\n\n"
                + "Nous avons le plaisir de vous informer que votre inscription sur winshot a bien été enregistrée avec succès. Vous pouvez désormais accéder à votre compte et profiter de nos services." +
                "Pour toute question ou assistance, n'hésitez pas à nous contacter à l'adresse suivante : djebbielaa037@gmail.com.\n" +
                "\n" +
                "Nous vous remercions pour votre inscription et restons à votre disposition pour toute information complémentaire.\n" +
                "\n" +
                "Cordialement,\n" +
                "L'équipe RH " +
                "Cliquez sur le lien ci-dessous pour valider votre compte :\n"
                + validationLink + "\n\n"
                + "Merci.";

        emailService.sendValidationEmail(
                user.getEmail(),
                "Validation de votre compte",
                emailBody
        );
    }

    /**
     * Validates a user by setting the "validated" flag to true.
     *
     * @param userId The ID of the user to validate.
     */
    public void validateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isValidated()) {
            throw new RuntimeException("User is already validated");
        }

        user.setValidated(true);
        userRepository.save(user);
    }
}
