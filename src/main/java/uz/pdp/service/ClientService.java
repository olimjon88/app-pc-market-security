package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.dto.ClientDto;
import uz.pdp.entity.Client;
import uz.pdp.repository.ClientRepository;
import uz.pdp.utils.ApiResponse;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import static uz.pdp.utils.ApiResponse.response;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public ResponseEntity<ApiResponse<List<Client>>> getAllInfo(Pageable pageable) {
        Page<Client> clientPage = clientRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        if (clientPage.isEmpty()){
            return response(HttpStatus.NOT_FOUND);
        }
        return response(clientPage.getContent(), clientRepository.count());
    }

    public ResponseEntity<ApiResponse<Client>> getById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.map(address -> response(address, HttpStatus.OK)).orElseGet(() -> response(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<ApiResponse<Client>> add(ClientDto dto){
        Client client = new Client();
        client.setFullName(dto.getFullName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setAddress(dto.getAddress());
        clientRepository.save(client);
        return ResponseEntity.ok(new ApiResponse<>("Client added"));
    }

    public ResponseEntity<ApiResponse<Client>> edit(Long id, ClientDto dto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return response(HttpStatus.NOT_FOUND);
        }

        Client client = optionalClient.get();
        client.setAddress(dto.getAddress());
        client.setEmail(dto.getEmail());
        client.setFullName(dto.getFullName());
        client.setPhoneNumber(dto.getPhoneNumber());

        clientRepository.save(client);
        return ResponseEntity.ok(new ApiResponse<>("Client edited"));
    }

    public ResponseEntity<ApiResponse<Client>> delete(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            return response(HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        clientRepository.delete(client);

        return ResponseEntity.ok(new ApiResponse<>("Client deleted"));
    }

}
