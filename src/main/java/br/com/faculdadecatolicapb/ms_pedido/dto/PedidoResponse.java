package br.com.faculdadecatolicapb.ms_pedido.dto;

import java.math.BigDecimal;

public record PedidoResponse(Long id, Long produtoId, Integer quantidade, BigDecimal valorTotal) {
}
