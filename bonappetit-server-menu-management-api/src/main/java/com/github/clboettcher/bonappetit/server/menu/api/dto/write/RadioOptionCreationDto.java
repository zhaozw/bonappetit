/*
 * Copyright (c) 2016 Claudius Boettcher (pos.bonappetit@gmail.com)
 *
 * This file is part of BonAppetit. BonAppetit is an Android based
 * Point-of-Sale client-server application for small restaurants.
 *
 * BonAppetit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BonAppetit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BonAppetit.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.clboettcher.bonappetit.server.menu.api.dto.write;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ApiModel(description = "An option that consists of multiple items of which one must be selected")
public class RadioOptionCreationDto extends OptionCreationDto {

    @ApiModelProperty(value = "The raiod itme that should be selected per default.", required = true)
    private RadioItemCreationDto defaultSelected;

    @ApiModelProperty(value = "The items that this option consists of.", required = true)
    private List<RadioItemCreationDto> radioItems;

    /**
     * Constructor setting the specified properties.
     *
     * @param title           see {@link #getTitle()}.
     * @param index           see {@link #index}.
     * @param defaultSelected see {@link #getDefaultSelected()}.
     * @param radioItems      see {@link #getRadioItems()}.
     */
    @Builder
    public RadioOptionCreationDto(String title,
                                  Integer index,
                                  RadioItemCreationDto defaultSelected,
                                  List<RadioItemCreationDto> radioItems) {
        super(title, index);
        this.defaultSelected = defaultSelected;
        this.radioItems = radioItems;
    }
}
