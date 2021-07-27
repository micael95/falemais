package br.com.uaubox.falemais.domain.usecases;

import br.com.uaubox.falemais.dto.response.PlanResponse;

import java.util.List;

public interface LoadPlan {
    List<PlanResponse> load();
}
