package com.shopapotheke.api.github.util;

import com.shopapotheke.api.github.entity.CodeRepo;
import com.shopapotheke.api.github.repository.CodeRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PopulateDBOnStart
        implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    CodeRepoRepository codeRepoRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        for(int i=1; i< 1000; i++){
            codeRepoRepository.save(new CodeRepo("Repo "+i,"Owner"+i, generateRandomEpoch(),
                    generateRandomLong(), getRandomProgrammingLanguage()));
        }
    }

    public long generateRandomEpoch() {
        Long tenYearsAgo = Instant.now().minus(Duration.ofDays(10 * 365)).getEpochSecond();
        Long tenDaysAgo = Instant.now().minus(Duration.ofDays(10)).getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(tenYearsAgo, tenDaysAgo);

        return Instant.ofEpochSecond(random).getEpochSecond();
    }

    public Long generateRandomLong(){
            long leftLimit = 500L;
            long rightLimit = 100000L;
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            return generatedLong;
    }

    public String getRandomProgrammingLanguage(){
            Random rand = new Random();
            String[] languages = {"go", "java", "c", "c#", "html", "markdown", "python"};
            return languages[rand.nextInt(languages.length)];
    }
}