package edu.strongsubgroup.agreement.api;

import edu.strongsubgroup.agreement.api.dto.ProviderDto;
import edu.strongsubgroup.agreement.repository.specification.ProviderSpecification;
import edu.strongsubgroup.agreement.service.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/providers/")
public class ProviderController {
    private final ProviderService providerService;

    @GetMapping("/{id}")
    public ProviderDto get(final @PathVariable Long id) {
        log.info("Get provider by id [{}]", id);
        return providerService.find(id);
    }

    @GetMapping("/")
    public Page<ProviderDto> get(final ProviderSpecification specification,
                                 @PageableDefault(sort = {"guid"}, direction = Sort.Direction.DESC, size = 20) final Pageable pageable) {
        log.info("Get providers by specification [{}]", specification);
        return providerService.findAllBySpecification(specification, pageable);
    }

    @PostMapping("/")
    public ProviderDto add(final @Valid @RequestBody ProviderDto providerDto) {
        log.info("Add provider [{}]", providerDto);
        return providerService.add(providerDto);
    }

    @PutMapping("/{id}")
    public ProviderDto update(final @Valid @RequestBody ProviderDto providerDto,
                              final @PathVariable Long id) {
        log.info("Update provider [{}] by id [{}]", providerDto, id);
        return providerService.update(providerDto, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(final @PathVariable Long id) {
        log.info("Delete provider by id [{}]", id);
        providerService.delete(id);
    }

    @PutMapping("/activate/{id}")
    public ProviderDto activate(final @PathVariable Long id) {
        log.info("Change status provider with id [{}]", id);
        return providerService.changeStatus(id);
    }
}
