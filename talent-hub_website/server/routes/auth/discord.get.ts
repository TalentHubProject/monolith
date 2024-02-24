export default oauth.discordEventHandler({
    async onSuccess(event, { user }) {

        await setUserSession(event, {
            user: {
                username: user.username,
                email: user.email,
                avatar: `https://cdn.discordapp.com/avatars/${user.id}/${user.avatar}.png`,
                banner: user.banner ? `https://cdn.discordapp.com/banners/${user.id}/${user.banner}.png` : null,
                banner_color: user.banner_color,
                global_name: user.global_name,
                loggedInAt: Date.now()
            },
        })

        return sendRedirect(event, '/')
    }
})