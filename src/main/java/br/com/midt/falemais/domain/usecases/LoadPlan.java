package br.com.midt.falemais.domain.usecases;

import br.com.midt.falemais.dto.response.PlanResponse;

import java.util.List;

public interface LoadPlan {
    List<PlanResponse> load();
}
