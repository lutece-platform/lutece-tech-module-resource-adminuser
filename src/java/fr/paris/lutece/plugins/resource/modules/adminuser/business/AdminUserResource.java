/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.resource.modules.adminuser.business;

import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;

/**
 * Admin user resource
 */
public class AdminUserResource extends AdminUser implements IResource
{
    private static final long serialVersionUID = -4011512795595150982L;
    private static final String CONSTANT_SPACE = " ";

    /**
     * Default constructor
     */
    public AdminUserResource( )
    {
        // Default constructor
    }

    /**
     * Creates a new admin user resource from an admin user
     * 
     * @param user
     *            The user
     */
    public AdminUserResource( AdminUser user )
    {
        setUserId( user.getUserId( ) );
        setAccessCode( user.getAccessCode( ) );
        setLastName( user.getLastName( ) );
        setFirstName( user.getFirstName( ) );
        setEmail( user.getEmail( ) );
        setStatus( user.getStatus( ) );
        setUserLevel( user.getUserLevel( ) );
        setPasswordReset( user.isPasswordReset( ) );
        setAccessibilityMode( user.getAccessibilityMode( ) );
        setPasswordMaxValidDate( user.getPasswordMaxValidDate( ) );
        setAccountMaxValidDate( user.getAccountMaxValidDate( ) );
        setDateLastLogin( user.getDateLastLogin( ) );
        setRights( user.getRights( ) );
        setRoles( AdminUserHome.getRolesListForUser( user.getUserId( ) ) );
        setAuthenticationService( user.getAuthenticationService( ) );
        setAuthenticationType( user.getAuthenticationType( ) );
        setLocale( user.getLocale( ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdResource( )
    {
        return Integer.toString( getUserId( ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceType( )
    {
        return AdminUser.RESOURCE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceName( )
    {
        return getFirstName( ) + CONSTANT_SPACE + getLastName( );
    }
}
