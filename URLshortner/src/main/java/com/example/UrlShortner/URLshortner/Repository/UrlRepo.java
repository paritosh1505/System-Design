package com.example.UrlShortner.URLshortner.Repository;

import com.example.UrlShortner.URLshortner.Models.UrlEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Repository
public interface UrlRepo extends JpaRepository<UrlEntry, Long> {

    @Query("Select u from UrlEntry u where u.encodedentry=:decodedEntry")
    public UrlEntry FetchOriginalUrl(String decodedEntry);
    @Modifying
    @Transactional
    @Query("Delete from UrlEntry u where u.encodedentry=:decodedEntry")
    void RemoveEntryFromDb(@Param("decodedEntry") String decodedEntry);
}
