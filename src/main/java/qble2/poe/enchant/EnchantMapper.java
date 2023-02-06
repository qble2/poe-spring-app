package qble2.poe.enchant;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EnchantMapper {

  @Named("toEnchantDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toEnchantDtoFromEntity")
  List<EnchantDto> toDtoListFromEntityList(List<Enchant> sourceList);

  @Named("toEnchantDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "tradeIds", source = "tradeIds")
  EnchantDto toDtoFromEntity(Enchant source);

}
