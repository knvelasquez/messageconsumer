package adapter.presentation;

import adapter.repository.MessageConsumerRepository;
import application.DecryptService;
import application.JwtService;
import application.MessageConsumerService;
import model.MessageConsumerEntity;
import model.SecurityKey;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageConsumerRest {

    private final MessageConsumerService messageConsumerService;
    private final JwtService jwtService;
    private final DecryptService decryptService;
    private final MessageConsumerRepository messageConsumerRepository;

    public MessageConsumerRest(MessageConsumerService messageConsumerService, JwtService jwtService, DecryptService decryptService, MessageConsumerRepository messageConsumerRepository) {
        this.messageConsumerService = messageConsumerService;
        this.jwtService = jwtService;
        this.decryptService = decryptService;
        this.messageConsumerRepository = messageConsumerRepository;
    }

    @GetMapping(value = "/all")
    public Page<MessageConsumerEntity> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return messageConsumerService.getAll(page, size);
    }

    @GetMapping(value = "/{id}")
    public String getById(@PathVariable long id,
                          @RequestHeader(value = "Authorization") String jwt) {
        SecurityKey securityKey = jwtService.decode(jwt.replace("Bearer ",""));
        MessageConsumerEntity message = messageConsumerRepository.findById(id);
        return decryptService.from(message.getContent(), securityKey.getPrivateKey());
    }
}
