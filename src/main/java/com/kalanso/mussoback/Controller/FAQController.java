package com.kalanso.mussoback.Controller;


import com.kalanso.mussoback.Model.FAQ;
import com.kalanso.mussoback.Service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faqs")
public class FAQController {
    @Autowired
    private FAQService faqService;

    @GetMapping
    public List<FAQ> getAllFAQs() {
        return faqService.getAllFAQs();
    }

    @GetMapping("/{id}")
    public FAQ getFAQById(@PathVariable Long id) {
        return faqService.getFAQById(id);
    }

    @PostMapping
    public FAQ createFAQ(@RequestBody FAQ faq) {
        return faqService.createFAQ(faq);
    }

    @PutMapping("/{id}")
    public FAQ updateFAQ(@PathVariable Long id, @RequestBody FAQ faq) {
        return faqService.updateFAQ(id, faq);
    }

    @DeleteMapping("/{id}")
    public void deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
    }
}
