package qble2.poe.item;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ItemPropertyMapper {

  @Named("toItemPropertyDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemPropertyDto toDtoFromGgg(ItemPropertyGgg source);

  /////
  /////
  /////

  @Named("toItemPropertyEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemProperty toEntityFromDto(ItemPropertyDto source);

  /////
  /////
  /////

  @Named("toItemPropertyDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemPropertyDto toDtoFromEntity(ItemProperty source);

}
