export default oauth.discordEventHandler({
    async onSuccess(event, { user }) {

        await setUserSession(event, {
            user: {
                username: user.username,
                email: user.email,
                avatar: `https://cdn.discordapp.com/avatars/${user.id}/${user.avatar}.png`
            },
            loggedInAt: Date.now()
        })

        return sendRedirect(event, '/')
    }
})