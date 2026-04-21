package br.com.faculdadecatolicapb.ms_pedido.mapper;

import br.com.faculdadecatolicapb.ms_pedido.domain.Pedido;
import br.com.faculdadecatolicapb.ms_pedido.dto.PedidoRequest;
import br.com.faculdadecatolicapb.ms_pedido.dto.PedidoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    Pedido toEntity(PedidoRequest dto);

    PedidoResponse toDTO(Pedido entity);
}
