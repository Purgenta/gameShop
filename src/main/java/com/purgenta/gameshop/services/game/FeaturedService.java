package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.repositories.IFeaturedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class FeaturedService implements IFeaturedService{
    private final IFeaturedRepository iFeaturedRepository;

    @Override
    public ResponseEntity<?>featuredGames() {
        return new ResponseEntity<>((iFeaturedRepository.findAll()), HttpStatus.OK);
    }
}
