package qble2.poe.item;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ItemRequirementMapper {

  @Named("toItemRequirementDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemRequirementDto toDtoFromGgg(ItemRequirementGgg source);

  /////
  /////
  /////

  @Named("toItemRequirementEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemRequirement toEntityFromDto(ItemRequirementDto source);

  /////
  /////
  /////

  @Named("toItemRequirementDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemRequirementDto toDtoFromEntity(ItemRequirement source);

}
