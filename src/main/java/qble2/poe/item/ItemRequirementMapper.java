package qble2.poe.item;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ItemRequirementMapper {

  // @Named("toItemRequirementDtoListFromGggList")
  // @IterableMapping(qualifiedByName = "toItemRequirementDtoFromGgg")
  // List<ItemRequirementDto> toDtoListFromGggList(List<ItemRequirementGgg> sourceList);

  @Named("toItemRequirementDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemRequirementDto toDtoFromGgg(ItemRequirementGgg source);

  /////
  /////
  /////

  // @Named("toItemRequirementEntityListFromDtoList")
  // @IterableMapping(qualifiedByName = "toItemRequirementEntityFromDto")
  // List<ItemRequirement> toEntityListFromDtoList(List<ItemRequirementDto> sourceList);

  @Named("toItemRequirementEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemRequirement toEntityFromDto(ItemRequirementDto source);

  /////
  /////
  /////

  // @Named("toItemRequirementDtoListFromEntityList")
  // @IterableMapping(qualifiedByName = "toItemRequirementDtoFromEntity")
  // List<ItemRequirementDto> toDtoListFromEntityList(List<ItemRequirement> sourceList);

  @Named("toItemRequirementDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "value", source = "value")
  ItemRequirementDto toDtoFromEntity(ItemRequirement source);

}
