package com.gracelogic.platform.user.service;

import com.gracelogic.platform.user.dto.AuthorizedUser;
import com.gracelogic.platform.user.exception.IllegalParameterException;
import com.gracelogic.platform.user.model.User;

/**
 * Author: Igor Parkhomenko
 * Date: 17.07.2016
 * Time: 22:47
 */
public abstract class AbstractLifecycleService implements UserLifecycleService {
    protected abstract UserService getUserService();

    @Override
    public User register(AuthorizedUser user, boolean trust) throws IllegalParameterException {
        return getUserService().register(user, trust);
    }

    @Override
    public void delete(User user) {
        getUserService().deleteUser(user);
    }
}