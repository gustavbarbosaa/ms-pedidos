package br.com.faculdadecatolicapb.ms_pedido.controllers;

import br.com.faculdadecatolicapb.ms_pedido.dto.ProdutoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produtos", url = "${feign.url}")
public interface ProdutoClient {
    @GetMapping("/{id}")
    ProdutoResponse buscarPorId(@PathVariable Long id);
}
