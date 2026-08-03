package org.course.service;

import org.course.entity.Client;
import org.course.entity.Profile;
import org.course.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(
            ClientRepository clientRepository
    ) {
        this.clientRepository = clientRepository;
    }

    public Long createClient(
            String name,
            String email,
            String address,
            String phone
    ) {
        Client client = new Client(name, email);
        Profile profile = new Profile(address, phone);
        client.setProfile(profile);

        this.clientRepository.save(client);
        return client.getId();
    }

    public boolean existsByName(String name) {
        return this.clientRepository.existsByName(name);
    }

    public boolean existsByEmail(String email) {
        return this.clientRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return this.clientRepository.existsByPhone(phone);
    }

    public Client findClientByName(String name) {
        return this.clientRepository.findByName(name);
    }

    public void updateProfile(Long clientId, String newAddress, String newPhone) {
        this.clientRepository.updateProfile(clientId, newAddress, newPhone);
    }

    public boolean deleteClientByName(String name) {
        return this.clientRepository.deleteByName(name);
    }
}
