package com.example.userservice.services;

import com.example.userservice.dtos.*;
import com.example.userservice.model.User;
import com.example.userservice.model.UserType;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenService tokenService;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

//        String[] token = authorization.split(" ");
//        System.out.println(token[1]);
//        Claims claims = tokenService.parseToken(token[1]);
//        Long id = claims.get("id", Long.class);
//        System.out.println(id);

    public User registerManager(RegisterManagerDto registerManagerDto) {

        User u = new User(registerManagerDto,passwordEncoder);

        Optional<User> existing = userRepository.findUsersByUsernameEqualsIgnoreCase(registerManagerDto.getUsername());

        if(existing.isPresent()){
            throw new IllegalArgumentException("Postoji vec sa ovim usernamom");
        }
        // todo poslati mejl za verifikovanje

        return userRepository.save(u);
    }

    public User registerClient(RegisterClientDto registerClientDto) {

        User u = new User(registerClientDto,passwordEncoder);

        Optional<User> existing = userRepository.findUsersByUsernameEqualsIgnoreCase(registerClientDto.getUsername());

        if(existing.isPresent()){
            throw new IllegalArgumentException("Postoji vec sa ovim usernamom");
        }
        // todo posalti imeal verify

        return userRepository.save(u);
    }

    public TokenResponseDto login(LoginDto loginDto) {
        // todo encode pass
        Optional<User> optionalUser = userRepository.findUserByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if(optionalUser.isPresent()){

            if(!optionalUser.get().isVerified()){
                throw new RuntimeException("Nisi verifikovan brootha :((");
            }
            if(!optionalUser.get().isActive()){
                throw new RuntimeException("Nije ti aktivan nalog brootha :((");
            }

            Claims claims = Jwts.claims();
            claims.put("id", optionalUser.get().getId());
            claims.put("role", optionalUser.get().getUserType().toString());
            //Generate token
            return new TokenResponseDto(tokenService.generate(claims));
        }
        throw new RuntimeException("No such user / pogresni kredenicijali");
    }

    // todo svuda ubaciti active koji su za klienta i managera jer moze da bude deaktriviran
    public User create(CreateUserDto createUserDto, PasswordEncoder passwordEncoder){

        User newUser;

        if(createUserDto instanceof CreateManagerDto){
            CreateManagerDto createManagerDto = (CreateManagerDto) createUserDto;
            newUser = new User(createManagerDto,passwordEncoder);
        }

        else{
            CreateClientDto createClientDto = (CreateClientDto) createUserDto;
            newUser = new User(createClientDto, passwordEncoder);
        }


        Optional<User> u = userRepository.findUsersByUsernameEqualsIgnoreCase(newUser.getUsername());
        if(u.isPresent()){
            throw new IllegalArgumentException("Postoji vec neko sa tim usernamemom");
        }
        // TODO POSLATI MEJL
        return this.userRepository.save(newUser);
    }

    public List<User> findAll(String token) {

        String[] tokens = token.split(" ");
//        System.out.println(tokens[1]); sve posle bearer
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);
        UserType userTypeToken = UserType.fromString(claims.get("role", String.class));

        if (userTypeToken.equals(UserType.ADMIN)){
            return userRepository.findAll();
        }
        return userRepository.findUsersByUserTypeEquals(UserType.CLIENT);

        // todo mozda dodati da client moze da dovuce sve managere??

    }

    public User getById(String token, Long id) {

        String[] tokens = token.split(" ");
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);
        UserType userTypeToken = UserType.fromString(claims.get("role", String.class));


        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            if (userTypeToken.equals(UserType.MANAGER)) {
                if (optionalUser.get().getUserType().equals(UserType.CLIENT)) {
                    return optionalUser.get();
                }
                else{
                    throw new RuntimeException("ne mozes ovog da vidis");
                }
            }
            else{
                return optionalUser.get();
            }
        }
        throw new RuntimeException("No user with that ID");
    }

    public User activateUser(Long id) {
        User u = userRepository.getById(id);
        u.setActive(true);
        return userRepository.save(u);
    }

    public User deactivateUser(String token, Long id) {

        String[] tokens = token.split(" ");
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);

        User u = userRepository.getById(id);

        if(idFromToken.equals(u.getId())){
            throw new RuntimeException("Ne mozes sam sebe da ugasis si debil?");
        }

        if(u.getUserType().equals(UserType.ADMIN)){
            throw new IllegalArgumentException("Ne mozes da gasis admina sine >:(");
        }

        u.setActive(false);
        return userRepository.save(u);
    }

    public User loyal(Long id) {
        User u = userRepository.getById(id);
        u.setLoyalty(true);

        return userRepository.save(u);
    }

    public User deloyal(Long id) {
        User u = userRepository.getById(id);
        u.setLoyalty(false);

        return userRepository.save(u);
    }

    public User editSelf(String token, EditClientDto editClientDto) {

        String[] tokens = token.split(" ");
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);

        User u = userRepository.getById(idFromToken);


        u.setFirst_name(editClientDto.getFirst_name() != null && !editClientDto.getFirst_name().isEmpty() ? editClientDto.getFirst_name() : u.getFirst_name());
        u.setLast_name(editClientDto.getLast_name() != null && !editClientDto.getLast_name().isEmpty() ? editClientDto.getLast_name() : u.getLast_name());

        if(editClientDto.getUsername() != null && !editClientDto.getUsername().isEmpty()) {
            Optional<User> exitingUsername = userRepository.findUsersByUsernameEqualsIgnoreCase(editClientDto.getUsername());
            if(exitingUsername.isPresent() && !exitingUsername.get().getId().equals(idFromToken)){
                throw new IllegalArgumentException("Ne moze vec ima taj username");
            }
            else{
                u.setUsername(editClientDto.getUsername());
            }
        }

        if(editClientDto.getEmail() != null && !editClientDto.getEmail().isEmpty()){
            if(!u.getEmail().equals(editClientDto.getEmail())) {
                u.setEmail(editClientDto.getEmail());
                u.setVerified(false);

                // todo posslati notifikaciju

            }
        }

        return userRepository.save(u);
    }

    // todo dodati ovde encoder i dekoder
    public User changePassword(String token, ChangePasswordDto changePasswordDto) {

        String[] tokens = token.split(" ");
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);
        UserType userTypeToken = UserType.fromString(claims.get("role", String.class));

        User u = userRepository.getById(idFromToken);

        if(userTypeToken.equals(UserType.ADMIN)){
            if(changePasswordDto.getOld_password().equals(u.getPassword())){
                u.setPassword(changePasswordDto.getNew_password());
                return userRepository.save(u);
            }
            else{
                throw new RuntimeException("nije dobra stara sifra");
            }
        }

        if(changePasswordDto.getOld_password().equals(u.getPassword()))
            u.setPassword(changePasswordDto.getNew_password());
        else{
            throw new RuntimeException("nije dobra stara sifra");
        }
        //todo ovde poslati email

        return userRepository.save(u);
    }


    public User editUser(String token, Long id, EditUserDto editUserDto, PasswordEncoder passwordEncoder) {

        String[] tokens = token.split(" ");
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);
        UserType userTypeToken = UserType.fromString(claims.get("role", String.class));

        if(idFromToken.equals(id))
            throw new IllegalArgumentException("Ne mozes sam sebe ovde da menjas ");


        User u = userRepository.getById(id);

        u.setFirst_name(editUserDto.getFirst_name() != null && !editUserDto.getFirst_name().isEmpty() ? editUserDto.getFirst_name() : u.getFirst_name());
        u.setLast_name(editUserDto.getLast_name() != null && !editUserDto.getLast_name().isEmpty() ? editUserDto.getLast_name() : u.getLast_name());


        if(editUserDto.getUsername() != null && !editUserDto.getUsername().isEmpty()) {
            Optional<User> exitingUsername = userRepository.findUsersByUsernameEqualsIgnoreCase(editUserDto.getUsername());
            if(exitingUsername.isPresent()){
                throw new IllegalArgumentException("Ne moze vec ima taj username");
            }
            else{
                u.setUsername(editUserDto.getUsername());
            }
        }
//        u.setUsername(editUserDto.getUsername() != null && !editUserDto.getUsername().isEmpty() ? editUserDto.getUsername() : u.getUsername());

        // todo ovo je sa encoderom
//        u.setPassword(editUserDto.getPassword() != null && !editUserDto.getPassword().isEmpty() ?  passwordEncoder.encode(editUserDto.getPassword()) : u.getPassword());
        u.setPassword(editUserDto.getPassword() != null && !editUserDto.getPassword().isEmpty() ?  editUserDto.getPassword() : u.getPassword());

        // todo ovde poslati notifikaciju korisniku da je promenjena sifra u sifru X

//        sendMail(u.getEmail());

        return userRepository.save(u);
    }

    public ResponseEntity<?> deteleUser(String token, Long id) {

        String[] tokens = token.split(" ");
        Claims claims = tokenService.parseToken(tokens[1]);
        Long idFromToken = claims.get("id", Long.class);
        UserType userTypeToken = UserType.fromString(claims.get("role", String.class));

        if(idFromToken.equals(id))
            throw new IllegalArgumentException("Si lud da brises sebe alo?");

        User u = userRepository.getById(id);
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
