/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.business.IResourceType;
import fr.paris.lutece.plugins.resource.business.ResourceTypeDefaultImplementation;
import fr.paris.lutece.plugins.resource.modules.adminuser.business.AdminUserResource;
import fr.paris.lutece.plugins.resource.service.ResourceCacheService;
import fr.paris.lutece.plugins.resource.service.provider.IResourceProvider;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.portal.service.i18n.I18nService;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


/**
 * Resource provider for admin users
 */
public class AdminUserResourceProvider implements IResourceProvider
{
    private static final String MESSAGE_ADMIN_USER_RESOURCE_TYPE_DESCRIPTION = "module.resource.adminuser.labelAdminUserResourceType";
    private List<IResourceType> _listResourceTypes;

    /**
     * Default constructor
     */
    public AdminUserResourceProvider(  )
    {
        _listResourceTypes = new ArrayList<IResourceType>( 1 );
        _listResourceTypes.add( new ResourceTypeDefaultImplementation( AdminUser.RESOURCE_TYPE,
                I18nService.getLocalizedString( MESSAGE_ADMIN_USER_RESOURCE_TYPE_DESCRIPTION, Locale.getDefault(  ) ) ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IResourceType> getResourceTypeList(  )
    {
        return _listResourceTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isResourceTypeManaged( String strResourceTypeName )
    {
        return AdminUser.RESOURCE_TYPE.equals( strResourceTypeName );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IResource getResource( String strIdResource, String strResourceTypeName )
    {
        String strCacheKey = ResourceCacheService.getResourceCacheKey( strIdResource, strResourceTypeName );
        AdminUserResource user = (AdminUserResource) ResourceCacheService.getInstance(  ).getFromCache( strCacheKey );

        if ( user == null )
        {
            if ( StringUtils.isNotEmpty( strIdResource ) && StringUtils.isNumeric( strIdResource ) )
            {
                int nIdAdminUser = Integer.parseInt( strIdResource );
                user = new AdminUserResource( AdminUserHome.findByPrimaryKey( nIdAdminUser ) );
                ResourceCacheService.getInstance(  ).putInCache( strCacheKey, user );
            }
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IResource> getListResources( String strResourceTypeName )
    {
        if ( AdminUser.RESOURCE_TYPE.equals( strResourceTypeName ) )
        {
            Collection<AdminUser> listAdminUser = AdminUserHome.findUserList(  );
            List<IResource> listResource = new ArrayList<IResource>( listAdminUser.size(  ) );

            for ( AdminUser adminUser : listAdminUser )
            {
                listResource.add( new AdminUserResource( adminUser ) );
            }

            return listResource;
        }

        return new ArrayList<IResource>(  );
    }
}
