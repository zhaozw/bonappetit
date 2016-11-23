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
package com.github.clboettcher.bonappetit.server.menu.api;

import com.github.clboettcher.bonappetit.server.core.error.ErrorResponse;
import com.github.clboettcher.bonappetit.server.menu.api.dto.MenuDto;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Public rest interface to manage menus.
 */
@Path("/")
@Api(value = "menus")
@ApiResponses(
        @ApiResponse(
                code = 404,
                message = "If a requested resource does not exist.",
                response = ErrorResponse.class
        )
)
public interface MenuManagement {

    /**
     * Relative path of the current menu resource.
     */
    String CURRENT_MENU_PATH = "/currentMenu";
    /**
     * Relative path of the menu resource.
     */
    String MENUS_PATH = "/menus";

    /**
     * Returns the currently active menu.
     *
     * @return A menu.
     */
    @GET
    @Path(CURRENT_MENU_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns the currently active menu.")
    @ApiResponses(
            @ApiResponse(
                    code = 500,
                    message = "If no current menu has been configured.",
                    response = ErrorResponse.class
            )
    )
    MenuDto getCurrentMenu();

    /**
     * Returns the menu with the given ID.
     *
     * @param id The ID to look for.
     * @return A menu.
     */
    @GET
    @Path(MENUS_PATH + "/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns the menu with the given ID.")
    @ApiResponses(
            @ApiResponse(
                    code = 400,
                    message = "If param id is blank.",
                    response = ErrorResponse.class
            )
    )
    MenuDto getMenuById(@ApiParam(value = "The menu ID to look for.", allowableValues = "0,*", required = true)
                        @PathParam("id") Long id);

    /**
     * Creates a menu from the given data.
     *
     * @param menuDto The menu transfer object.
     * @return A response indicating the success of the operation.
     */
    @POST
    @Path(MENUS_PATH)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Creates the given menu in the database.")
    @ApiResponses(
            @ApiResponse(
                    code = 400,
                    message = "The request did not contain a menu to create.",
                    response = ErrorResponse.class
            )
    )
    Response createMenu(@ApiParam(value = "The menu to create.", required = true) MenuDto menuDto);
}