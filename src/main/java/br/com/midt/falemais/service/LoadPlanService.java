package br.com.midt.falemais.service;

import br.com.midt.falemais.domain.model.Plan;
import br.com.midt.falemais.domain.repository.PlanRepository;
import br.com.midt.falemais.domain.usecases.LoadPlan;
import br.com.midt.falemais.dto.response.PlanResponse;
import br.com.midt.falemais.factory.FactoryManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoadPlanService extends FactoryManager implements LoadPlan {

    private final PlanRepository planRepository;

    public LoadPlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<PlanResponse> load() {
        List<Plan> planList = this.planRepository.findAll();
        if (!planList.isEmpty())
            return planList.stream().map(plan -> getResponseFromObject(plan, PlanResponse.class)).collect(Collectors.toList());
        else
            return new ArrayList<>();
    }
}
