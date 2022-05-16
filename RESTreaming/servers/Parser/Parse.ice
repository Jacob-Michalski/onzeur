module Parse
{
    struct Resparse {
        string action;
        string title;
        string artist;
    }

    interface Parser
    {
        Resparse parseRequest(string request);
    }
}
