module Streaming
{
    struct response {
        string action;
        string titre;
        string artiste;
        string imgUrl;
        string ref;
        string statusCode;
    }

    interface Parser
    {
        response parseRequest(string request);
    }
}