package edu.strongsubgroup.agreement.api;

import edu.strongsubgroup.agreement.api.dto.MerchantDto;
import edu.strongsubgroup.agreement.repository.specification.MerchantSpecification;
import edu.strongsubgroup.agreement.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/merchants/")
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/{id}")
    public MerchantDto get(final @PathVariable Long id) {
        log.info("Get merchant by id [{}]", id);
        return merchantService.find(id);
    }

    @GetMapping("/")
    public Page<MerchantDto> get(final MerchantSpecification specification,
                                 @PageableDefault(sort = {"guid"}, direction = Sort.Direction.DESC, size = 20) final Pageable pageable) {
        log.info("Get merchants by specification [{}]", specification);
        return merchantService.findAllBySpecification(specification, pageable);
    }

    @PostMapping("/")
    public MerchantDto add(final @Valid @RequestBody MerchantDto merchantDto) {
        log.info("Add merchant [{}]", merchantDto);
        return merchantService.add(merchantDto);
    }

    @PutMapping("/{id}")
    public MerchantDto update(final @Valid @RequestBody MerchantDto merchantDto,
                              final @PathVariable Long id) {
        log.info("Update merchant [{}] by id [{}]", merchantDto, id);
        return merchantService.update(merchantDto, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(final @PathVariable Long id) {
        log.info("Delete merchant by id [{}]", id);
        merchantService.delete(id);
    }
}
