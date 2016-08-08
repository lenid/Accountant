package accountant.models.converters;

import accountant.constants.Profile;
import accountant.models.db.ProfileDb;
import accountant.models.db.UserDb;
import accountant.models.ui.UserUi;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lehr0416 on 05-Aug-16.
 */

public class UserDbToUiConverter implements Converter<UserDb, UserUi> {

    @Override
    public UserUi convert(UserDb userDb) {
        UserUi userUi = new UserUi();

        userUi.setId(userDb.getId());
        userUi.setSsoId(userDb.getSsoId());
        userUi.setPasswdEncoded(userDb.getPasswd());
        userUi.setFirstName(userDb.getFirstName());
        userUi.setLastName(userDb.getLastName());
        userUi.setEmail(userDb.getEmail());
        userUi.setCreated(userDb.getCreated());

        Set<ProfileDb> profileDbSet = userDb.getProfiles();
        Set<Profile> profileSet = profileDbSet.stream().map((pDb) -> Profile.valueOf(pDb.getProfile().toUpperCase())).collect(Collectors.toSet());
        userUi.setProfiles(profileSet);

        return userUi;
    }
}
