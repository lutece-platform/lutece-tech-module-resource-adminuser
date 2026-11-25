/*
 * Copyright (c) 2002-2022, City of Paris
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
package fr.paris.lutece.plugins.resource.modules.adminuser.service.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.test.LuteceTestCase;
import jakarta.enterprise.inject.spi.CDI;

/**
 * Administrators offered as ADMIN_USER resources (e.g. « affecter une ressource » workflow task): active ones only
 * (decision 25/11)
 */
public class AdminUserResourceProviderTest extends LuteceTestCase
{
    private static AdminUser create( String strSuffix, int nStatus )
    {
        AdminUser user = new AdminUser( );
        user.setAccessCode( "la06_" + strSuffix + "_" + System.nanoTime( ) );
        user.setLastName( "la06" );
        user.setFirstName( strSuffix );
        user.setEmail( "la06." + strSuffix + "@example.org" );
        user.setStatus( nStatus );
        user.setLocale( java.util.Locale.FRENCH );
        AdminUserHome.create( user );
        return user;
    }

    @Test
    public void testOnlyActiveAdministratorsListed( )
    {
        AdminUser active = create( "active", AdminUser.ACTIVE_CODE );
        AdminUser disabled = create( "disabled", AdminUser.NOT_ACTIVE_CODE );
        AdminUser anonymized = create( "anonymized", AdminUser.ANONYMIZED_CODE );
        try
        {
            AdminUserResourceProvider provider = CDI.current( ).select( AdminUserResourceProvider.class ).get( );
            List<String> ids = provider.getListResources( AdminUser.RESOURCE_TYPE ).stream( ).map( IResource::getIdResource ).collect( Collectors.toList( ) );

            assertTrue( ids.contains( String.valueOf( active.getUserId( ) ) ), ids.toString( ) );
            assertFalse( ids.contains( String.valueOf( disabled.getUserId( ) ) ), "disabled administrator listed" );
            assertFalse( ids.contains( String.valueOf( anonymized.getUserId( ) ) ), "anonymized administrator listed" );
        }
        finally
        {
            for ( AdminUser user : List.of( active, disabled, anonymized ) )
            {
                AdminUserHome.remove( user.getUserId( ) );
            }
        }
    }
}
