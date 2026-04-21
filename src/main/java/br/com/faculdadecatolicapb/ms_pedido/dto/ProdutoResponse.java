package br.com.faculdadecatolicapb.ms_pedido.dto;

import java.math.BigDecimal;

public record ProdutoResponse(Long id, String nome, BigDecimal valor) {
}
