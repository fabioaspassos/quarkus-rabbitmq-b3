package com.acme.fixedIncome;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CheckDocumentPriorityService {
    private final Map<String, Integer> documentsPriority;

    public CheckDocumentPriorityService() {
        this.documentsPriority = new HashMap<String, Integer>();
        this.documentsPriority.put("97188167044", 2);
        this.documentsPriority.put("19760218046", 3);
    }

    public int execute(String documentNumber) {
        return this.documentsPriority.getOrDefault(documentNumber, 0);
    }
}
