package uz.pdp.controller;

import io.swagger.annotations.Api;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dto.ClientDto;
import uz.pdp.entity.Client;
import uz.pdp.service.ClientService;
import uz.pdp.utils.ApiResponse;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

   private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasAuthority('GET_ALL')")
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Client>>> getAll(@RequestParam (value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam (value = "size", required = false, defaultValue = "5") Integer size){
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        return clientService.getAllInfo(pageable);
    }

    @PreAuthorize("hasAuthority('GET')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Client>> getById(@PathVariable Long id){
        return clientService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADD')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Client>> add(@RequestBody ClientDto dto){
        return clientService.add(dto);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Client>> edit(@PathVariable Long id, @RequestBody ClientDto dto){
        return clientService.edit(id, dto);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Client>> delete(@PathVariable Long id){
        return clientService.delete(id);
    }


}
