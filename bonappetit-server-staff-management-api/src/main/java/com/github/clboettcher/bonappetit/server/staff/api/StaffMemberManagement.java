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
package com.github.clboettcher.bonappetit.server.staff.api;

import com.github.clboettcher.bonappetit.server.core.error.ErrorResponse;
import com.github.clboettcher.bonappetit.server.staff.api.dto.StaffMemberCreationDto;
import com.github.clboettcher.bonappetit.server.staff.api.dto.StaffMemberDto;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/" + StaffMemberManagement.ROOT_PATH)
@Api
public interface StaffMemberManagement {

    String TAG = "staffMembers";
    String ROOT_PATH = "staffMembers";

    /**
     * Gets all staff members.
     *
     * @return A list of staff members, may be empty.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a list of staff members.", tags = {TAG})
    List<StaffMemberDto> getStaffMembers();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns the staff member with the given ID.", tags = {TAG})
    @ApiResponses(
            @ApiResponse(code = 404, message = "No staff member was found for the given ID.",
                    response = ErrorResponse.class)
    )
    StaffMemberDto getStaffMember(@ApiParam(value = "The ID to look for.") @PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Creates a staff member.", tags = {TAG})
    Response createStaffMember(@ApiParam(value = "The staff member to create.", required = true)
                               StaffMemberCreationDto staffMemberDto);
}
