package edu.strongsubgroup.agreement.api;

import edu.strongsubgroup.agreement.api.dto.AgreementDto;
import edu.strongsubgroup.agreement.api.dto.AgreementHistoryDto;
import edu.strongsubgroup.agreement.repository.specification.AgreementSpecification;
import edu.strongsubgroup.agreement.service.AgreementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agreements/")
public class AgreementController {

    private final AgreementService agreementService;

    @GetMapping("/{id}")
    public AgreementDto get(final @PathVariable Long id) {
        log.info("Get agreement by id [{}]", id);
        return agreementService.find(id);
    }

    @GetMapping("/{id}/history")
    public List<AgreementHistoryDto> getHistory(final @PathVariable Long id) {
        log.info("Get agreements history by id [{}]", id);
        return agreementService.findHistoryByAgreementId(id);
    }

    @GetMapping("/")
    public Page<AgreementDto> get(final AgreementSpecification specification,
                                  @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC, size = 20) final Pageable pageable) {
        log.info("Get agreements by specification [{}]", specification);
        return agreementService.findAllBySpecification(specification, pageable);
    }

    @PostMapping("/")
    public AgreementDto add(final @Valid @RequestBody AgreementDto agreementDto) {
        log.info("Add agreement [{}]", agreementDto);
        return agreementService.add(agreementDto);
    }

    @PutMapping("/{id}")
    public AgreementDto update(final @Valid @RequestBody AgreementDto agreementDto,
                               final @PathVariable Long id) {
        log.info("Update agreement [{}] by id [{}]", agreementDto, id);
        return agreementService.update(agreementDto, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(final @PathVariable Long id) {
        log.info("Delete agreement by id [{}]", id);
        agreementService.delete(id);
    }
}
