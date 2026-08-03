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

    public boolean isClientExistByName(String name) {
        return this.clientRepository.existsByName(name);
    }

    public boolean isClientExistByEmail(String email) {
        return this.clientRepository.existsByEmail(email);
    }

    public boolean isProfileExistByPhone(String phone) {
        return this.clientRepository.existsByPhone(phone);
    }

    public boolean deleteClientByName(String name) {
        return this.clientRepository.deleteByName(name);
    }
}
