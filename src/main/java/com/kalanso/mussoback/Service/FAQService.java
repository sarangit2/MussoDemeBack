package com.kalanso.mussoback.Service;



import com.kalanso.mussoback.Model.FAQ;
import com.kalanso.mussoback.Repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQService {
    @Autowired
    private FAQRepository faqRepository;

    public List<FAQ> getAllFAQs() {
        return faqRepository.findAll();
    }

    public FAQ getFAQById(Long id) {
        return faqRepository.findById(id).orElseThrow();
    }

    public FAQ createFAQ(FAQ faq) {
        return faqRepository.save(faq);
    }

    public FAQ updateFAQ(Long id, FAQ faq) {
        FAQ existing = faqRepository.findById(id).orElseThrow();
        existing.setQuestion(faq.getQuestion());
        existing.setRéponse(faq.getRéponse());
        existing.setDateMiseAJour(faq.getDateMiseAJour());
        return faqRepository.save(existing);
    }

    public void deleteFAQ(Long id) {
        faqRepository.deleteById(id);
    }
}

